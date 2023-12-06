import "./style.css";
import {  home,
  homeSelecionado,
  paciente,
  pacienteSelecionado,
  linhaVertical,
  medico,
  medicoSelecionado} from "../../assets";
import useUsersList from "../../hooks/useUsersList";

export const Menu = () => {
  const {
    menuSelecionado,
    setMenuSelecionado,
    setNomeClienteInput,
    setEmailClienteInput,
    setCpfClienteInput,
    setTelefoneClienteInput,
    setEndereçoClienteInput,
    setComplementoClienteInput,
    setCepClienteInput,
    setBairroClienteInput,
    setCidadeClienteInput,
    setUfClienteInput,
  } = useUsersList();

  const handleMenu = () => {
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
    <header className="menuLateral">
      <div className="contemOpcaoMenu">
        <img
          src={menuSelecionado === "home" ? homeSelecionado : home}
          alt="home"
          className="opcaoMenu"
          onClick={() => {
            setMenuSelecionado("home");
            handleMenu();
          }}
        />
        <img
          src={linhaVertical}
          alt="linha vertical"
          className={
            menuSelecionado === "home" ? "linhaVertical" : "esconde"
          }
        />
      </div>
      <div className="contemOpcaoMenu">
        <img
          src={menuSelecionado === "pacientes" ? pacienteSelecionado : paciente}
          alt="pacientes"
          onClick={async () => {
            setMenuSelecionado("pacientes");
            handleMenu();
          }}
          className="opcaoMenu"
        />
        <img
          src={linhaVertical}
          alt="linha vertical"
          className={
            menuSelecionado === "pacientes" ? "linhaVertical" : "esconde"
          }
        />
      </div>
      <div className="contemOpcaoMenu">
        <img
          src={
            menuSelecionado === "medicos" ? medicoSelecionado : medico
          }
          className="opcaoMenu"
          alt="medicos"
          id="menuMedicos"
          onClick={() => {
            setMenuSelecionado("medicos");
            handleMenu();
          }}
          
        />
        <img
          src={linhaVertical}
          alt="linha vertical"
          className={
            menuSelecionado === "medicos" ? "linhaVertical" : "esconde"
          }
        />
      </div>
    </header>
  );
}
