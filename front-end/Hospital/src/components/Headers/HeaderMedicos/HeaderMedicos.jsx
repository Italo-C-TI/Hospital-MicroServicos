import "./style.css";
import useUsersList from "../../../hooks/useUsersList";
import {lupa,medico} from "../../../assets";

export const HeaderMedicos = ()=> {
  const { 
    setModalCadastroMedicoAberto,
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
          <img src={medico} alt="medico-icone" className="icon" />
          <h3>Medicos</h3>
        </div>
 
        <div className="boxAdicionaEPesquisa">
            <button
              className="botaoAdicionaCliente"
              onClick={() => {
                setModalCadastroMedicoAberto(true);
              }}
            >
              <span>+ Adicionar Medico </span>
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