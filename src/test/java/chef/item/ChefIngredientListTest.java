package chef.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefIngredientListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/chef/item/ingredient.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void inventorListIngredients(final int testIndex, final String name, final String type, final String unit, final String code, final String description, final String retailPrice, final String link) {
	
		super.signIn("chef1", "chef1");
		
		super.clickOnMenu("Chef", "My Ingredients");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(testIndex, 0, name);
		super.checkColumnHasValue(testIndex, 1, type);
		
		super.clickOnListingRecord(testIndex);
				
		super.checkFormExists();
		super.checkInputBoxHasValue("name", name);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("unit", unit);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);
		
	}
	
}
