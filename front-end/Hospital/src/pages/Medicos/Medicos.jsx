import { ModalCadastroMedico,HeaderMedicos } from '../../components';
import useUsersList from '../../hooks/useUsersList';


export const Medicos =()=> {
  const {modalCadastroMedicoAberto} =
useUsersList();
  return (
    <div>
      <HeaderMedicos/>
      {modalCadastroMedicoAberto && (<ModalCadastroMedico/>)}

    </div>
  );
}