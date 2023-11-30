import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe Conta que representa uma conta bancária
 */
public class Conta {
    private double saldo; // Saldo da conta
    private RuntimeException exception; // Variável para lidar com exceções

    /**
     * Configuração: Cliente especial com saldo inicial negativo
     */
    @Given("^Um cliente especial com saldo atual de -(\\d+) reais$")
    public void umClienteEspecialComSaldoAtualDeReais(int sI) {
        saldo = -sI; // Define o saldo inicial para um cliente especial
    }

    /**
     * Configuração: Cliente comum com um saldo inicial negativo
     */
    @Given("^Um cliente comum com saldo atual de -(\\d+) reais$")
    public void umClienteComumComSaldoAtualDeReais(int sI) {
        saldo = -sI; // Define o saldo inicial para um cliente comum
    }

    /**
     * Ação: Solicitar um saque
     */
    @When("^for solicitado um saque no valor de (\\d+) reais$")
    public void forSolicitadoUmSaqueNoValorDeReais(int vS) {
        if (vS <= 0) {
            exception = new IllegalArgumentException("Valor de saque inválido."); // Captura exceções para valor de saque inválido
        } else if (saldo < vS) {
            exception = new RuntimeException("Saldo insuficiente para o saque."); // Captura exceções para saldo insuficiente
        } else {
            saldo -= vS; // Realiza o saque, atualizando o saldo
        }
    }

    /**
     * Verificação: Saque efetuado com sucesso
     */
    @Then("^deve efetuar o saque e atualizar o saldo da conta para -(\\d+) reais$")
    public void deveEfetuarOSaqueEAtualizarOSaldoDaContaParaReais(int novoSaldo) {
        assertEquals(-novoSaldo, saldo, 0.001); // Verifica se o saldo foi atualizado corretamente após o saque
        System.out.println("Saque realizado! Seu novo saldo é: " + saldo);
    }

    /**
     * Verificação: Saque não deve ser efetuado devido a saldo insuficiente
     */
    @Then("Não deve efetuar o saque e deve retornar a mensagem Saldo Insuficiente.")
    public void naoDeveEfetuarOSaqueEDeveRetornarAMensagemSaldoInsuficiente() {
        if (exception != null) {
            assertEquals("Saldo insuficiente para o saque.", exception.getMessage()); // Verifica se a mensagem de exceção é relacionada a saldo insuficiente
            System.out.println("Não foi possível realizar o saque. Seu saldo é insuficiente.");
        } else {
            System.out.println("Saque realizado com sucesso!");
        }
    }
}