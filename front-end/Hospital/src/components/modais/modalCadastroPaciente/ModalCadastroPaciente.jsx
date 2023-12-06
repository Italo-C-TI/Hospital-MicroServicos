import "./style.css";
import {paciente as pacienteIcone , fechar} from "../../../assets";
import useUsersList from "../../../hooks/useUsersList";
import InputMask from "react-input-mask";

export const ModalCadastroPaciente = ()=> {
  const {
    setModalCadastroPacienteAberto,
    setMensagem,
    setSucesso,
    handleMostraMensagem,
    setMostraMensagem,
    nomeClienteInput,
    setNomeClienteInput,
    emailClienteInput,
    setEmailClienteInput,
    cpfClienteInput,
    setCpfClienteInput,
    telefoneClienteInput,
    setTelefoneClienteInput,
    endereçoClienteInput,
    setEndereçoClienteInput,
    complementoClienteInput,
    setComplementoClienteInput,
    cepClienteInput,
    setCepClienteInput,
    bairroClienteInput,
    setBairroClienteInput,
    cidadeClienteInput,
    setCidadeClienteInput,
    ufClienteInput,
    setUfClienteInput,
    handleViaCEP,
    urlPacientes
  } = useUsersList();

  const handleTelefoneMask = () => {
    if (telefoneClienteInput) {
      if (telefoneClienteInput[4] === "9") {
        return "(99)99999-9999";
      }
    }
    return "(99)9999-9999";
  };

  const handleTelefoneSize = () => {
    if (telefoneClienteInput) {
      if (telefoneClienteInput[4] === "9") {
        return "11";
      }
    }
    return "10";
  };

  const handleBlur = (estado, id, id2) => {
    if (
      (id === "cpfCliente" || id === "editarCpfCliente") &&
      cpfClienteInput.replace(/\D/g, "").length < 11
    ) {
      document.getElementById(id).style.border = "1px solid #E70000";
      document.getElementById(id2).textContent = "Informe um cpf valido";
      return;
    }

    if (
      (id === "telefoneCliente" || id === "editarTelefoneCliente") &&
      telefoneClienteInput.replace(/\D/g, "").length < handleTelefoneSize()
    ) {
      document.getElementById(id).style.border = "1px solid #E70000";
      document.getElementById(id2).textContent = "Informe um telefone valido";
      return;
    }

    if (
      (id === "cepCliente" || id === "editarCepCliente") &&
      cepClienteInput.replace(/\D/g, "").length < 8 &&
      cepClienteInput.replace(/\D/g, "").length > 0
    ) {
      document.getElementById(id).style.border = "1px solid #E70000";
      document.getElementById(id2).textContent = "Informe um cep valido";
      return;
    }

    if (estado) {
      document.getElementById(id).style.border = "1px solid #D0D5DD";
      document.getElementById(id2).textContent = "";
      return;
    }

    document.getElementById(id).style.border = "1px solid #E70000";
    document.getElementById(id2).textContent = "Este campo deve ser preenchido";
  };

  const checkValues = () => {
    if (!nomeClienteInput) {
      setSucesso(false);
      setMensagem("Nome deve ser preenchido");
      handleMostraMensagem();
      return;
    }

    if (!emailClienteInput) {
      setSucesso(false);
      setMensagem("Email deve ser valido");
      handleMostraMensagem();
      return;
    }

    if (cpfClienteInput.replace(/\D/g, "").length < 11) {
      setSucesso(false);
      setMensagem("Cpf invalido");
      handleMostraMensagem();
      return;
    }

    if (telefoneClienteInput.replace(/\D/g, "").length < handleTelefoneSize()) {
      setSucesso(false);
      setMensagem("Telefone invalido");
      handleMostraMensagem();
      return;
    }

    if (
      cepClienteInput.replace(/\D/g, "").length < 8 &&
      cepClienteInput.replace(/\D/g, "").length > 0
    ) {
      setSucesso(false);
      setMensagem("Cep invalido");
      handleMostraMensagem();
      return;
    }

    handleCadastroCliente();
  };

  async function handleCadastroCliente() {
    try {
      const response = await fetch(
        `${urlPacientes}/cadastrar`,
        {
          method: "POST",
          headers: {
            "content-type": "application/json",
          },
          body: JSON.stringify({
            nome: nomeClienteInput.trim(),
            email: emailClienteInput.trim(),
            cpf: cpfClienteInput.toString().replace(/\D/g, "").trim(),
            telefone: telefoneClienteInput.toString().replace(/\D/g, "").trim(),
            logradouro: endereçoClienteInput.trim(),
            cep: cepClienteInput.replace(/\D/g, "").trim(),
            bairro: bairroClienteInput.trim(),
            cidade: cidadeClienteInput.trim(),
            complemento: complementoClienteInput.trim(),
            estado: ufClienteInput.trim(),
          }),
        }
      );
      const data = await response.json();

      setMensagem(data.mensagem);
      setSucesso(data.sucesso);
      if (data.sucesso) {
        setModalCadastroPacienteAberto(false);
        setMostraMensagem(true);
      } else {
        handleMostraMensagem();
        setModalCadastroPacienteAberto(true);
      }
    } catch (error) {
      setSucesso(false);
      setMensagem(error.message);
      setModalCadastroPacienteAberto(true);
      handleMostraMensagem();
    }
  }

  const handleCancelaCadastroCliente = () => {
    setModalCadastroPacienteAberto(false);
    setNomeClienteInput("");
    setEmailClienteInput("");
    setCpfClienteInput("");
    setTelefoneClienteInput("");
    setEndereçoClienteInput("");
    setComplementoClienteInput("");
    setCepClienteInput("");
    setBairroClienteInput("");
    setCidadeClienteInput("");
    setUfClienteInput("");
  };

  return (
    <div className="fundoModalCadastroCliente">
      <div className="modalCadastroCliente">
        <img
          className="fechaModalCadastroCliente"
          src={fechar}
          alt="x"
          onClick={() => {
            handleCancelaCadastroCliente();
            setModalCadastroPacienteAberto(false);
          }}
        />
        <div className="cabecalhoModalCadastroCliente">
          <img src={pacienteIcone} alt="clientes-icone" />
          <h2>{"Cadastrar Paciente"}</h2>
        </div>

        <div className="contemInputsModalCadastroCliente">
          <label htmlFor={"nomeCliente"}>
            Nome*
          </label>
          <input
            className="inputGrande"
            id={"nomeCliente"}
            type="text"
            required
            onBlur={() =>
              handleBlur(
                nomeClienteInput,
                "nomeCliente",
                "spanNome"
              )
            }
            placeholder="Digite o nome"
            onChange={(event) =>
              setNomeClienteInput(event.target.value.replace(/[^A-Za-z ]/g, ""))
            }
            value={nomeClienteInput}
          />
          <span
            id={"spanNome"}
            className="alerta"
          ></span>
          <label htmlFor={"emailCliente"}>
            Email*
          </label>
          <input
            className="inputGrande"
            id={"emailCliente"}
            type="email"
            required
            onBlur={() =>
              handleBlur(
                emailClienteInput,
                "emailCliente",
                "spanAlerta"
              )
            }
            placeholder="Digite o e-mail"
            onChange={(event) => setEmailClienteInput(event.target.value)}
            value={emailClienteInput}
          />
          <span
            id={"spanAlerta"}
            className="alerta"
          ></span>
          <div className="contemInputsPequenos">
            <div className="contemInputPequeno">
              <label htmlFor="cpfCliente">CPF*</label>
              <InputMask
                id={"cpfCliente"}
                mask="999.999.999-99"
                type="text"
                required
                onBlur={() =>
                  handleBlur(
                    cpfClienteInput,
                    "cpfCliente",
                    "spanCpf"
                  )
                }
                placeholder="Digite o CPF"
                onChange={(event) => setCpfClienteInput(event.target.value)}
                value={cpfClienteInput}
              />
              <span
                id={"spanCpf"}
                className="alerta"
              ></span>
            </div>
            <div className="contemInputPequeno">
              <label htmlFor="telefoneCliente">Telefone*</label>
              <InputMask
                id={"telefoneCliente"}
                mask={handleTelefoneMask()}
                type="text"
                required
                onBlur={() =>
                  handleBlur(
                    telefoneClienteInput,
                    "telefoneCliente",
                    "spanTelefone"
                  )
                }
                placeholder="Digite o Telefone"
                onChange={(event) =>
                  setTelefoneClienteInput(event.target.value)
                }
                value={telefoneClienteInput}
              />
              <span
                id={"spanTelefone"}
                className="alerta"
              ></span>
            </div>
          </div>

          <label htmlFor="endereçoCliente">Endereço</label>
          <input
            className="inputGrande"
            id="endereçoCliente"
            type="text"
            placeholder="Digite o endereço"
            onChange={(event) => setEndereçoClienteInput(event.target.value)}
            value={endereçoClienteInput}
          />
          <label htmlFor="complementoCliente">Complemento</label>
          <input
            className="inputGrande"
            id="complementoCliente"
            type="text"
            placeholder="Digite o complemento"
            onChange={(event) => setComplementoClienteInput(event.target.value)}
            value={complementoClienteInput}
          />

          <div className="contemInputsPequenos">
            <div className="contemInputPequeno">
              <label htmlFor="cepCliente">CEP</label>
              <InputMask
                id={"cepCliente"}
                mask="99.999-999"
                type="text"
                onBlur={() =>
                  handleBlur(
                    cepClienteInput,
                    "cepCliente",
                    "spanCep"
                  )
                }
                placeholder="Digite o CEP"
                onChange={(event) => handleViaCEP(event.target.value)}
                value={cepClienteInput}
              />
              <span
                id={"spanCep"}
                className="alerta"
              ></span>
            </div>

            <div className="contemInputPequeno">
              <label htmlFor="bairroCliente">Bairro</label>
              <input
                id="bairroCliente"
                type="text"
                placeholder="Digite o bairro"
                onChange={(event) => setBairroClienteInput(event.target.value)}
                value={bairroClienteInput}
              />
            </div>
          </div>

          <div className="contemInputsPequenos">
            <div className="contemInputPequeno">
              <label htmlFor="cidadeCliente">Cidade</label>
              <input
                id="cidadeCliente"
                type="text"
                placeholder="Digite a Cidade"
                onChange={(event) => setCidadeClienteInput(event.target.value)}
                value={cidadeClienteInput}
              />
            </div>
            <div className="contemInputPequenoUF">
              <label htmlFor="UFCliente">UF</label>
              <input
                id="UFCliente"
                type="text"
                placeholder="Digite a UF"
                onChange={(event) => setUfClienteInput(event.target.value)}
                value={ufClienteInput}
              />
            </div>
          </div>

          <div className="contemCancelarAplicar">
            <button
              className="cancelaCadastroCliente"
              onClick={handleCancelaCadastroCliente}
            >
              Cancelar
            </button>
            <button className="aplicaCadastroCliente" onClick={checkValues}>
              Aplicar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
