import { useNavigate } from "react-router-dom";
import * as Styled from "./NotFound.styles.jsx"

export const NotFound = ()=> {

  const navigate = useNavigate();

  return (
    <Styled.NotFoundBackground onClick={() => navigate("/home")}/>
  )
}
