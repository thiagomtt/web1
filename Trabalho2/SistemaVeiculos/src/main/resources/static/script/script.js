function doToggle() {
    cliente = document.getElementById("cliente");
    loja = document.getElementById("loja");

    btn = document.getElementById("btn");
    logCli = document.getElementById("logCli");
    logLoja = document.getElementById("logLoja");

    cliente.classList.toggle("none");
    loja.classList.toggle("none");

    logCli.classList.toggle("none");
    logLoja.classList.toggle("none");
}
