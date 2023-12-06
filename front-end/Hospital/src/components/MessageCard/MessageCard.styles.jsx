import styled from 'styled-components';

export const ContainerCardMensagem = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 15px 16px;
    box-shadow: 0px 4px 42px rgba(0, 0, 0, 0.2);
    border-radius: 10px;
    width: 330px;
    height: 54px;
    background: ${props => (props.sucesso ? '#35508d' : '#c25967')};
    position: absolute;
    right: 7.5%;
    bottom: 15%;
    z-index: 5;

    img {
        position: absolute;
        right: 15px;
        top: 7px;
    }

    span {
        font-family: 'Nunito', sans-serif;
        font-size: 14px;
        line-height: 19px;
        color: #ffffff;
        max-width: 275px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
`;