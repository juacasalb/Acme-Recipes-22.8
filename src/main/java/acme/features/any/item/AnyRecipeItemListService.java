package acme.features.any.item;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Item;
import acme.entities.Quantity;
import acme.entities.Recipe;
import acme.features.any.recipe.AnyRecipeRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyRecipeItemListService implements AbstractListService<Any, Item>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyRecipeRepository repository;

	// AbstractListService<Any, Recipe> ---------------------------
	
	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		
		int id;
		id = request.getModel().getInteger("id");
		final Recipe recipe = this.repository.findOneRecipeById(id);
		return recipe.isPublished();
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		assert request != null;
		
		final Collection<Item> result = new HashSet<Item>();
		int recipeId;
		recipeId = request.getModel().getInteger("id");
		final Collection<Quantity> quantities = this.repository.findQuantityByRecipeId(recipeId);
		
		for(final Quantity q:quantities) {
			final int quantityId = q.getId();
			final Collection<Item> items = this.repository.findManyItemsByQuantityId(quantityId);
			result.addAll(items);
		}
		return result;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "name","type");
		
	}
	
}
