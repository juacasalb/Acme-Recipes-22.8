package acme.features.administrator.dashboard;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import acme.entities.fineDish.State;
import acme.features.any.item.AnyItemRepository;
import acme.features.chef.pimpam.ChefPimpamRepository;
import acme.forms.AdministratorDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdminDashboardShowService implements AbstractShowService<Administrator, AdministratorDashboard>{
	
	@Autowired
	protected AdminDashboardRepository repository;
	
	@Autowired
	protected AnyItemRepository itemRepository;


	//si es chef
	@Autowired
	protected ChefPimpamRepository pimpamRepository;

	//si es epicure
//	@Autowired
//	protected EpicurePimpamRepository pimpamRepository;
	
	@Override
	public boolean authorise(final Request<AdministratorDashboard> request) {
		assert request != null;
		return true;
	}

	@Override
	public AdministratorDashboard findOne(final Request<AdministratorDashboard> request) {
		assert request != null;
		
		final AdministratorDashboard adminDashboard = new AdministratorDashboard();
		final Integer totalNIngredients;
		final Integer totalNKitchenUtensils;
		final Map<State, Integer> totalNDishesByStatus;
		
		final Map<String,Double> averageRetailPriceIngredientsByCurrency;
		final Map<String,Double> averageRetailPriceKitchenUtensilsByCurrency;
		final Map<Pair<State, String>, Double> averageBudgetDishesByStatus;
		
		final Map<String,Double> deviationRetailPriceIngredientsByCurrency;
		final Map<String,Double> deviationRetailPriceKitchenUtensilsByCurrency;
		final Map<Pair<State, String>, Double> deviationBudgetDishesByStatus;
		
		final Map<String,Double> minRetailPriceIngredientsByCurrency;
		final Map<String,Double> minRetailPriceKitchenUtensilsByCurrency;
		final Map<Pair<State, String>, Double> minBudgetDishesByStatus;
		
		final Map<String,Double> maxRetailPriceIngredientsByCurrency;
		final Map<String,Double> maxRetailPriceKitchenUtensilsByCurrency;
		final Map<Pair<State, String>, Double> maxBudgetDishesByStatus;
		
		totalNIngredients = this.repository.totalNIngredients();
		totalNKitchenUtensils = this.repository.totalNKitchenUtensils();
		totalNDishesByStatus = this.repository.totalNDishesByStatus().stream().collect(Collectors.toMap(x-> (State)x[0],x->((Long)x[1]).intValue()));
		
		averageRetailPriceIngredientsByCurrency = this.repository.averageRetailPriceIngredientsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		averageRetailPriceKitchenUtensilsByCurrency = this.repository.averageRetailPriceKitchenUtensilsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		averageBudgetDishesByStatus = this.repository.averageBudgetDishesByStatus().stream()
			.collect(Collectors.toMap(x->Pair.of((State)x[0], (String)x[1]), x->(Double) x[2]));
		
		deviationRetailPriceIngredientsByCurrency = this.repository.deviationRetailPriceIngredientsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		deviationRetailPriceKitchenUtensilsByCurrency = this.repository.deviationRetailPriceKitchenUtensilsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		deviationBudgetDishesByStatus = this.repository.deviationBudgetDishesByStatus().stream()
			.collect(Collectors.toMap(x->Pair.of((State)x[0], (String)x[1]), x->(Double) x[2]));
		
		minRetailPriceIngredientsByCurrency = this.repository.minRetailPriceIngredientsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		minRetailPriceKitchenUtensilsByCurrency = this.repository.minRetailPriceKitchenUtensilsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		minBudgetDishesByStatus = this.repository.minBudgetDishesByStatus().stream()
			.collect(Collectors.toMap(x->Pair.of((State)x[0], (String)x[1]), x->(Double) x[2]));
		
		maxRetailPriceIngredientsByCurrency = this.repository.maxRetailPriceIngredientsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		maxRetailPriceKitchenUtensilsByCurrency = this.repository.maxRetailPriceKitchenUtensilsByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));
		maxBudgetDishesByStatus = this.repository.maxBudgetDishesByStatus().stream()
			.collect(Collectors.toMap(x->Pair.of((State)x[0], (String)x[1]), x->(Double) x[2]));
		
		
		adminDashboard.setTotalNIngredients(totalNIngredients);
		adminDashboard.setTotalNKitchenUtensils(totalNKitchenUtensils);
		adminDashboard.setTotalNDishesByStatus(totalNDishesByStatus);
		adminDashboard.setAverageRetailPriceIngredientsByCurrency(averageRetailPriceIngredientsByCurrency);
		adminDashboard.setAverageRetailPriceKitchenUtensilsByCurrency(averageRetailPriceKitchenUtensilsByCurrency);
		adminDashboard.setAverageBudgetDishesByStatus(averageBudgetDishesByStatus);
		adminDashboard.setDeviationRetailPriceIngredientsByCurrency(deviationRetailPriceIngredientsByCurrency);
		adminDashboard.setDeviationRetailPriceKitchenUtensilsByCurrency(deviationRetailPriceKitchenUtensilsByCurrency);
		adminDashboard.setDeviationBudgetDishesByStatus(deviationBudgetDishesByStatus);
		adminDashboard.setMinRetailPriceIngredientsByCurrency(minRetailPriceIngredientsByCurrency);
		adminDashboard.setMinRetailPriceKitchenUtensilsByCurrency(minRetailPriceKitchenUtensilsByCurrency);
		adminDashboard.setMinBudgetDishesByStatus(minBudgetDishesByStatus);
		adminDashboard.setMaxRetailPriceIngredientsByCurrency(maxRetailPriceIngredientsByCurrency);
		adminDashboard.setMaxRetailPriceKitchenUtensilsByCurrency(maxRetailPriceKitchenUtensilsByCurrency);
		adminDashboard.setMaxBudgetDishesByStatus(maxBudgetDishesByStatus);
		
		
		//CC -------------------------------------------------------------------------------------------------------------

		//si es Ingredient
//		final Double ratio = ((double)this.pimpamRepository.findIngredientWithPimpam().size()/(double)this.itemRepository.findAllIngredients().size());

		//si es kitchenUtensil
//		final Double ratio = ((double)this.pimpamRepository.findKitchenUtensilWithPimpam().size()/(double)this.itemRepository.findAllKitchenUtensils().size());
		
		//si es item
		final Double ratio = ((double)this.pimpamRepository.findItemsWithPimpam().size()/(double)this.pimpamRepository.findAllItems().size());

		final Map<String,Double> averageBudgetByCurrency;
		final Map<String,Double> deviationBudgetByCurrency;
		final Map<String,Double> minBudgetByCurrency;
		final Map<String,Double> maxBudgetByCurrency;

		averageBudgetByCurrency = this.repository.averageBudgetByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));

		deviationBudgetByCurrency = this.repository.deviationBudgetByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));

		minBudgetByCurrency = this.repository.minBudgetByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));

		maxBudgetByCurrency = this.repository.maxBudgetByCurrency().stream()
			.collect(Collectors.toMap(x->(String)x[0], x->(Double) x[1]));

		adminDashboard.setRatio(ratio);
		adminDashboard.setAverageBudgetByCurrency(averageBudgetByCurrency);
		adminDashboard.setDeviationBudgetByCurrency(deviationBudgetByCurrency);
		adminDashboard.setMinBudgetByCurrency(minBudgetByCurrency);
		adminDashboard.setMaxBudgetByCurrency(maxBudgetByCurrency);

		//----------------------------------------------------------------------------------------------------------------
		return adminDashboard;
	}

	@Override
	public void unbind(final Request<AdministratorDashboard> request, final AdministratorDashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
				
		request.unbind(entity, model, "totalNIngredients",
			"totalNKitchenUtensils",
			"totalNDishesByStatus",
			"averageRetailPriceIngredientsByCurrency",
			"averageRetailPriceKitchenUtensilsByCurrency","averageBudgetDishesByStatus",
			"deviationRetailPriceIngredientsByCurrency","deviationRetailPriceKitchenUtensilsByCurrency","deviationBudgetDishesByStatus",
			"minRetailPriceIngredientsByCurrency","minRetailPriceKitchenUtensilsByCurrency","minBudgetDishesByStatus",
			"maxRetailPriceIngredientsByCurrency","maxRetailPriceKitchenUtensilsByCurrency","maxBudgetDishesByStatus",
			"ratio", "averageBudgetByCurrency", "deviationBudgetByCurrency", "minBudgetByCurrency", "maxBudgetByCurrency");
	}

}
