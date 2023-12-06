import { useState } from "react";

function useUsersListProvider() {
  const urlPacientes = "http://localhost:8082/pacientes/";
  const urlMedicos = "http://localhost:8081/medicos/";
  const urlConsultas = "http://localhost:8083/consultas/";
  const [menuSelecionado, setMenuSelecionado] = useState("home");
  const [sucesso, setSucesso] = useState(false);
  const [mensagem, setMensagem] = useState("");
  const [mostraMensagem, setMostraMensagem] = useState(false);
  const [modalAgendarConsultaAberto,setModalAgendarConsultaAberto] = useState(false);

  const [modalCadastroPacienteAberto, setModalCadastroPacienteAberto] =
    useState(false);
    const [modalCadastroMedicoAberto, setModalCadastroMedicoAberto] =
    useState(false);


  const [dadosNovaConsulta, setDadosNovaConsulta] = useState({
    cpfPaciente: "",
    crmMedico: "",
    dataHora: "",
  });
  
  const handleAgendarConsulta = async () => {
    try {
      const response = await fetch(
        `${urlConsultas}agendar`,
        {
          method: "POST",
          headers: {
            "content-type": "application/json",
            cpfPaciente:dadosNovaConsulta.cpfPaciente,
            crmMedico:dadosNovaConsulta.crmMedico,
            dataHora:dadosNovaConsulta.dataHora 
          },
        }
      );

      const data = await response.json();
      if (data.sucesso === false) {
        setSucesso(false);
        setMensagem(data.mensagem);
        handleMostraMensagem();
      } else {
        setModalAgendarConsultaAberto(false);
        setSucesso(true);
        setMensagem(data.mensagem);
        handleMostraMensagem();

      }
    } catch (error) {
      setMensagem(error.message);
      setSucesso(false);
      handleMostraMensagem();
    }
  };

 
  const [listaPacientes, setListaPacientes] = useState([]);

  const handleGetListaPacientes = async () => {
    try {
      const response = await fetch(
       `${urlPacientes}listar`,
        {
          method: "GET",
          headers: {
            "content-type": "application/json",
          },
        }
      );
      const pacientes = await response.json();


      setListaPacientes(pacientes);
    } catch (error) {
      setSucesso(false);
      setMensagem(error.message);
      handleMostraMensagem();
    }
  };

  const [dadosDoCep, setDadosDoCep] = useState();

  const handleViaCEP = async (cep) => {
    setCepClienteInput(cep);
    if (cep.replace(/\D/g, "").length !== 8) {
      return;
    }

    try {
      const response = await fetch(
        `https://viacep.com.br/ws/${cep.replace(/\D/g, "")}/json/`,
        {
          method: "GET",
          headers: {
            "content-type": "application/json",
          },
        }
      );

      const data = await response.json();
      if (data.erro) {
        return;
      }
      setDadosDoCep(data);
      setBairroClienteInput(data.bairro);
      setEndereçoClienteInput(data.logradouro);
      setComplementoClienteInput(data.complemento);
      setCidadeClienteInput(data.localidade);
      setUfClienteInput(data.uf);
    } catch (error) { console.log(error.message)}
  };

  const handleAbreModalCadastroPaciente = () => {
    setModalCadastroPacienteAberto(true);
  };

  const handleMostraMensagem = () => {
    setMostraMensagem(true);
    setTimeout(() => {
      setMostraMensagem(false);
    }, 250000);
  };
  const [nomeClienteInput, setNomeClienteInput] = useState("");
  const [emailClienteInput, setEmailClienteInput] = useState("");
  const [cpfClienteInput, setCpfClienteInput] = useState("");
  const [telefoneClienteInput, setTelefoneClienteInput] = useState("");
  const [endereçoClienteInput, setEndereçoClienteInput] = useState("");
  const [complementoClienteInput, setComplementoClienteInput] = useState("");
  const [cepClienteInput, setCepClienteInput] = useState("");
  const [bairroClienteInput, setBairroClienteInput] = useState("");
  const [cidadeClienteInput, setCidadeClienteInput] = useState("");
  const [ufClienteInput, setUfClienteInput] = useState("");

  return {
    menuSelecionado,setMenuSelecionado,
    mensagem,setMensagem,
    mostraMensagem,setMostraMensagem,
    sucesso,setSucesso,
    handleMostraMensagem,
    handleAbreModalCadastroPaciente,
    listaPacientes,setListaPacientes,
    handleGetListaPacientes,
    nomeClienteInput,setNomeClienteInput,
    emailClienteInput,setEmailClienteInput,
    cpfClienteInput,setCpfClienteInput,
    telefoneClienteInput,setTelefoneClienteInput,
    endereçoClienteInput,setEndereçoClienteInput,
    complementoClienteInput,setComplementoClienteInput,
    cepClienteInput,setCepClienteInput,
    bairroClienteInput,setBairroClienteInput,
    cidadeClienteInput,setCidadeClienteInput,
    ufClienteInput, setUfClienteInput,
    dadosDoCep,setDadosDoCep,
    handleViaCEP,
    handleAgendarConsulta,
    modalAgendarConsultaAberto,setModalAgendarConsultaAberto,
    urlMedicos,
    modalCadastroPacienteAberto, setModalCadastroPacienteAberto,
    modalCadastroMedicoAberto,setModalCadastroMedicoAberto,
    dadosNovaConsulta,setDadosNovaConsulta
  };
}
export default useUsersListProvider;
