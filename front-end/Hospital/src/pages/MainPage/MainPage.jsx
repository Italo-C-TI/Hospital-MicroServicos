import useUsersList from "../../hooks/useUsersList";
import {Menu} from "../../components"
import * as Styled from './MainPage.styles';
import {Medicos} from '../Medicos';
import {Home} from '../Home/Home';
import {Pacientes} from '../Pacientes';

export const MainPage = ()=> {
  const {  menuSelecionado} =
  useUsersList();
  // useEffect(() => {
  //     const carregarDados = async () => {
  //       if (gotData === false) {
  //         await handleGetListarMedicos();
  //         await handleGetListarPacientes();
  //         setGotData(true);
  //       }
  //     }
  //   carregarDados()

  // }, [navigate, gotData, handleGetListarMedicos, setGotData, handleGetListarPacientes])

  const handleRender = () => {
    return menuSelecionado === "medicos" ? ( 
      <Medicos />
    ) : menuSelecionado === "pacientes" ? (
      <Pacientes />
    ) : (
      <Home />
    );
  };

  return (
    <Styled.Container>
      <Menu />
      {/* {mostraMensagem && <MessageCard />} */}
      {handleRender()}
    </Styled.Container>
  );
}
