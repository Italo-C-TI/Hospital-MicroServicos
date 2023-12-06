import { Routes, Route } from "react-router-dom";
import { NotFound } from "./components/naoEncontrado/NotFound";
import { MainPage } from "./pages/MainPage/MainPage";

function App() {
  return (
    <Routes>
      <Route path="/" exact element={<MainPage />} />
      <Route path="/medicos" exact element={<MainPage />} />
      <Route path="/pacientes" exact element={<MainPage />} />
      <Route path="*" exact element={<NotFound />} />
    </Routes>
  );
}
export default App;

