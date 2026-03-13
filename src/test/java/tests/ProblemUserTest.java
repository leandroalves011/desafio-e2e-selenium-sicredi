package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.FakerUtils;

import java.util.List;

import utils.SortOption;
import utils.UserType;

public class ProblemUserTest extends BaseUserTest {

    @Test
    void CT12_ProblemUser_shouldInventoryDisplayedSuccessfully() {
        initializePages();
        login(UserType.PROBLEM);

        Assertions.assertTrue(
                pages.inventoryPage().isInventoryDisplayed(),
                "Inventory deveria ser exibido após login."
        );
    }

    @Test
    void CT13_ProblemUser_shouldHaveDifferentImages() {
        initializePages();
        login(UserType.PROBLEM);

        Assertions.assertFalse(
                pages.inventoryPage().allImagesAreEqual(),
                "BUG: imagens dos produtos deveriam ser diferentes."
        );
    }

    @Test
    void CT14_ProblemUser_shouldAddTwoProductsToCart() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBackpackToCart();
        pages.inventoryPage().addBikeLightToCart();
        pages.inventoryPage().goToCart();

        Assertions.assertEquals(
                2,
                pages.cartPage().getCartItemCount(),
                "BUG: Deveria ser possível adicionar dois produtos ao carrinho."
        );
    }

    @Test
    void CT15_ProblemUser_shouldRemoveProductFromCart() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBackpackToCart();
        pages.inventoryPage().goToCart();
        pages.cartPage().removeItem();

        Assertions.assertEquals(
                0,
                pages.cartPage().getCartItemCount(),
                "Produto deveria ser removido corretamente do carrinho."
        );
    }

    @Test
    void CT16_ProblemUser_shouldCompleteCheckout() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBikeLightToCart();
        pages.inventoryPage().goToCart();
        pages.cartPage().proceedToCheckout();

        pages.checkoutPage().fillForm(
                FakerUtils.firstName(),
                FakerUtils.lastName(),
                FakerUtils.zipCode()
        );

        Assertions.assertTrue(driver.getCurrentUrl().contains("checkout-complete"),
                "BUG: problem_user não consegue finalizar checkout."
        );
    }

    @Test
    void CT17_ProblemUser_shouldLogoutSuccessfully() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().logout();

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "Usuário deveria voltar para tela inicial após logout."
        );
    }

    @Test
    void CT18_ProblemUser_shouldOrderProductsSuccessfully() {
        initializePages();
        login(UserType.PROBLEM);

        List<String> beforeSort = pages.inventoryPage().getProductNames();

        pages.inventoryPage().sortProducts(SortOption.NAME_DESC);

        List<String> afterSort = pages.inventoryPage().getProductNames();

        List<String> expectedSorted = beforeSort
                .stream()
                .sorted((a, b) -> b.compareTo(a))
                .toList();

        Assertions.assertEquals(
                expectedSorted,
                afterSort,
                "BUG: ordenação Z-A não funciona corretamente para problem_user."
        );
    }

    @Test
    void CT19_ProblemUser_shouldRedirectToAboutLinkPage() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().clickAbout();

        Assertions.assertFalse(
                pages.inventoryPage().isOnSauceLabsPage(),
                "BUG: botão About não redirecionou corretamente."
        );
    }

    @Test
    void CT20_ProblemUser_cartShouldResetAfterLogout() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBackpackToCart();

        Assertions.assertEquals(
                1,
                pages.inventoryPage().getCartItemCount()
        );

        pages.inventoryPage().logout();

        login(UserType.PROBLEM);

        Assertions.assertEquals(
                0,
                pages.inventoryPage().getCartItemCount(),
                "BUG: carrinho permaneceu com itens após logout."
        );
    }

    @Test
    void CT21_ProblemUser_cartBadgeShouldUpdateCorrectly() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBackpackToCart();

        Assertions.assertEquals(
                1,
                pages.inventoryPage().getCartItemCount()
        );

        pages.inventoryPage().removeFirstProductFromCart();

        Assertions.assertEquals(
                0,
                pages.inventoryPage().getCartItemCount(),
                "BUG: badge do carrinho não atualizou após remover item."
        );
    }

    @Test
    void CT22_ProblemUser_CheckoutShouldDisplayCorrectSummary() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBackpackToCart();
        pages.inventoryPage().goToCart();
        pages.cartPage().proceedToCheckout();

        pages.checkoutPage().fillForm(
                "Leandro",
                "Alves",
                "12345"
        );
        Assertions.assertTrue(
                pages.checkoutPage().isSummaryDisplayed(),
                "BUG: summary do checkout não aparece corretamente."
        );
    }

    @Test
    void CT23_ProblemUser_ShouldAddItems3_4_6FromInventory() {

        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBoltTShirtToCart();
        pages.inventoryPage().addFleeceJacketToCart();
        pages.inventoryPage().addRedTshirtToCart();

        pages.inventoryPage().goToCart();

        Assertions.assertEquals(
                3,
                pages.cartPage().getCartItemCount(),
                "BUG: itens 3,4,6 não foram adicionados ao carrinho."
        );
    }

    @Test
    void CT24_ProblemUser_ShouldAddItemsThroughItemPage() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().openItemByIndex(2);
        pages.inventoryPage().addItemFromItemPage();
        pages.inventoryPage().goToCart();

        Assertions.assertEquals(
                1,
                pages.cartPage().getCartItemCount(),
                "Item deveria ser adicionado através da página do produto."
        );
    }

    @Test
    void CT25_ProblemUser_InventoryItemShouldMatchItemPage() {
        initializePages();
        login(UserType.PROBLEM);

        String inventoryName = pages.inventoryPage().getProductNameByIndex(0);

        pages.inventoryPage().openItemByIndex(0);

        String itemPageName = pages.inventoryPage().getItemPageTitle();

        Assertions.assertEquals(
                inventoryName,
                itemPageName,
                "BUG: item da página não corresponde ao item do inventário."
        );
    }

    @Test
    void CT26_ProblemUser_BrokenItemPageShouldBreakCart() {
        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().openItemByIndex(3);

        String currentUrl = driver.getCurrentUrl();

        Assertions.assertFalse(
                currentUrl.contains("id=6"),
                "BUG: item 4 abriu página do item errado."
        );

        pages.inventoryPage().addItemFromItemPage();
        pages.inventoryPage().goToCart();

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("cart"),
                "BUG: aplicação travou ao abrir carrinho."
        );
    }

    @Test
    void CT27_ProblemUser_ShouldNotClearFirstNameField() {

        initializePages();
        login(UserType.PROBLEM);

        pages.inventoryPage().addBackpackToCart();
        pages.inventoryPage().goToCart();

        pages.cartPage().proceedToCheckout();

        String firstName = FakerUtils.firstName();
        String lastName = FakerUtils.lastName();

        pages.checkoutPage().typeFirstName(firstName);
        pages.checkoutPage().typeLastName(lastName);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String value = pages.checkoutPage().getFirstNameValue();

        Assertions.assertEquals(
                firstName,
                value,
                "BUG: preencher lastName apaga o campo firstName."
        );
    }
}