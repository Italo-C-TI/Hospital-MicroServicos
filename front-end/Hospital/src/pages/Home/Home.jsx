import useUsersList from "../../hooks/useUsersList";

export const Home = ()=> { 
   const {     modalAgendarConsultaAberto,
    setModalAgendarConsultaAberto} =
useUsersList();
  return (
    <div >
        <h1 style={{ margin: '10px 30px'}}>Agende já uma consulta</h1>
        <div style={{display:"flex", width:"100vw",alignItems:'center', justifyContent:'center' ,backgroundColor: "white", marginTop:'100px'}}>
          <img style={{width: '1000px', height:'500px'}} src={"https://blog.iclinic.com.br/wp-content/uploads/2017/09/otimizar-agendamento-de-consultas-na-clinica.jpg"}/>
        </div>
        <div style={{display:"flex",alignItems:'center', justifyContent:'center' , marginTop: '30px'}}>
        <button style={{ backgroundColor: '#da0175'}}
              onClick={() => {
                setModalAgendarConsultaAberto(true);
              }}
            >
              <span>+ Agendar consulta </span>
            </button>
        </div>


            {/* {modalAgendarConsultaAberto &&

            }; */}
    </div>


  )
};
