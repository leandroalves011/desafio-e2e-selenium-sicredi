package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.FakerUtils;

import java.util.List;

import utils.SortOption;
import utils.UserType;

public class StandardUserTest extends BaseUserTest {

    @Test
    void CT01_StandardUser_shouldLoginSuccessfully() {
        initializePages();
        login(UserType.STANDARD);

        Assertions.assertTrue(
                pages.inventoryPage().isInventoryDisplayed(),
                "Inventory deveria ser exibido após login."
        );
    }

    @Test
    void CT02_StandardUser_imagesShouldNotBeDuplicated() {
        initializePages();
        login(UserType.STANDARD);

        Assertions.assertFalse(
                pages.inventoryPage().allImagesAreEqual(),
                "Imagens dos produtos deveriam ser diferentes para cada item."
        );
    }

    @Test
    void CT03_StandardUser_shouldAddTwoProductsToCart() {
        initializePages();
        login(UserType.STANDARD);

        pages.inventoryPage().addBackpackToCart();
        pages.inventoryPage().addOnesieToCart();
        pages.inventoryPage().goToCart();

        Assertions.assertEquals(
                2,
                pages.cartPage().getCartItemCount(),
                "Dois produtos deveriam estar no carrinho."
        );
    }

    @Test
    void CT04_StandardUser_shouldRemoveProductFromCart() {
        initializePages();
        login(UserType.STANDARD);

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
    void CT05_StandardUser_shouldCompleteCheckout() {

        initializePages();
        login(UserType.STANDARD);

        pages.inventoryPage().addBackpackToCart();
        pages.inventoryPage().goToCart();

        pages.cartPage().proceedToCheckout();

        pages.checkoutPage().fillForm(
                FakerUtils.firstName(),
                FakerUtils.lastName(),
                FakerUtils.zipCode()
        );

        // valida apenas que o checkout avançou
        Assertions.assertTrue(
                driver.getCurrentUrl().contains("checkout"),
                "Checkout deveria avançar após preencher o formulário."
        );
    }

    @Test
    void CT06_StandardUser_shouldLogoutSuccessfully() {
        initializePages();
        login(UserType.STANDARD);

        pages.inventoryPage().logout();

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "Usuário deveria retornar para página inicial após logout."
        );
    }

    @Test
    void CT07_StandardUser_shouldSortProductsByNameDescending() {
        initializePages();
        login(UserType.STANDARD);

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
                "Ordenação por nome Z-A deveria funcionar corretamente."
        );
    }

    @Test
    void CT08_StandardUser_shouldRedirectToSauceLabsAboutPage() {
        initializePages();
        login(UserType.STANDARD);

        pages.inventoryPage().clickAbout();

        Assertions.assertTrue(
                pages.inventoryPage().isOnSauceLabsPage(),
                "Botão About deveria redirecionar para o site da Sauce Labs."
        );
    }

    @Test
    void CT09_StandardUser_cartShouldResetAfterLogout() {
        initializePages();
        login(UserType.STANDARD);

        pages.inventoryPage().addBackpackToCart();

        Assertions.assertEquals(
                1,
                pages.inventoryPage().getCartItemCount()
        );

        pages.inventoryPage().logout();

        login(UserType.STANDARD);

        Assertions.assertEquals(
                0,
                pages.inventoryPage().getCartItemCount(),
                "Carrinho deveria ser resetado após logout."
        );
    }

    @Test
    void CT10_StandardUser_cartBadgeShouldUpdateCorrectly() {
        initializePages();
        login(UserType.STANDARD);

        pages.inventoryPage().addBackpackToCart();

        Assertions.assertEquals(
                1,
                pages.inventoryPage().getCartItemCount()
        );

        pages.inventoryPage().removeFirstProductFromCart();

        Assertions.assertEquals(
                0,
                pages.inventoryPage().getCartItemCount(),
                "Badge do carrinho deveria atualizar após remover item."
        );
    }

    @Test
    void CT11_StandardUser_checkoutShouldDisplayCorrectSummary() {

        initializePages();
        login(UserType.STANDARD);

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
                "Checkout summary deveria aparecer após preencher o formulário."
        );
    }

    @Test
    void exploratory01_StandardUser_shouldValidateProductConsistency() {

        initializePages();
        login(UserType.STANDARD);
        List<String> inventoryProducts =
                pages.inventoryPage().getProductNames();
        for (int i = 0; i < inventoryProducts.size(); i++) {
            String inventoryName =
                    pages.inventoryPage().getProductNameByIndex(i);
            pages.inventoryPage().openItemByIndex(i);
            String itemPageName =
                    pages.inventoryPage().getItemPageTitle();
            Assertions.assertEquals(
                    inventoryName,
                    itemPageName,
                    "Produto inconsistente detectado"
            );
            driver.navigate().back();
        }
    }

    @Test
    void exploratory02_StandardUser_shouldValidateCatalogIntegrity() {

        initializePages();
        login(UserType.STANDARD);
        List<String> products =
                pages.inventoryPage().getProductNames();
        Assertions.assertEquals(
                6,
                products.size(),
                "Catálogo deveria conter 6 produtos"
        );
    }
}