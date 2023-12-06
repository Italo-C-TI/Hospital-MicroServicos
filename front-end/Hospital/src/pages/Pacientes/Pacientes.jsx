import { HeaderPacientes } from "../../components/Headers/HeaderPacientes";
import useUsersList from '../../hooks/useUsersList';
import {ModalCadastroPaciente} from '../../components'

export const Pacientes = ()=> {
  const {modalCadastroPacienteAberto} =
useUsersList();
  return (
    <div>
     <HeaderPacientes/>

    {modalCadastroPacienteAberto && (<ModalCadastroPaciente/>)}
    </div>
  );
}
