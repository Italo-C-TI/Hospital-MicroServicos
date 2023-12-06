import useUsersList from '../../hooks/useUsersList.jsx';
import * as Styled from './MessageCard.styles.jsx';

export const MessageCard = () => {
    const { sucesso, mensagem, setMostraMensagem } = useUsersList();
    return (
        <Styled.ContainerCardMensagem sucesso={sucesso} onClick={() => setMostraMensagem(false)}>
            <img src={""} alt="fechar" />
            <span>{mensagem}</span>
        </Styled.ContainerCardMensagem>
    );
};