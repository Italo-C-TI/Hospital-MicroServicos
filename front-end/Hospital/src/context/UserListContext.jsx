import { createContext } from "react";
import useUsersListProvider from "../hooks/useUsersProvider";

const UsersListContext = createContext();

export function UsersListProvider(props) {
  const valuesProvider = useUsersListProvider();
  return (
    <UsersListContext.Provider value={valuesProvider}>
      {props.children}
    </UsersListContext.Provider>
  );
}


export default UsersListContext;
