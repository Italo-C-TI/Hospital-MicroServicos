import "./style.css";
import useUsersList from "../../../hooks/useUsersList";
import {lupa,paciente} from "../../../assets";

export const HeaderPacientes = ()=> {
  const { 
    setModalCadastroPacienteAberto
  } = useUsersList();


  const handleChange = (event) => {
        console.log(event);
  }

  return (
    <>
      <div className="header">
      </div>
      <hr className="linhaHorizontal" />
      <div className="utilidadesPaginaClientes">
        <div className="boxIconeNome">
          <img src={paciente} alt="paciente-icone" />
          <h3>{"Pacientes"}</h3>
        </div>
 
        <div className="boxAdicionaEPesquisa">
            <button
              className="botaoAdicionaCliente"
              onClick={() => {
                setModalCadastroPacienteAberto(true);
              }}
            >
              <span>+ Adicionar Paciente </span>
            </button>

          <div className="contemInputPesquisa">
            <input onChange={(event) => handleChange(event)} className="pesquisa" type="text" placeholder="Pesquisa" />
            <img src={lupa} alt="lupa" />
          </div>
        </div>
      </div>
    </ >
  )
};