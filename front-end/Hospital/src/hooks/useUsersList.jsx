import { useContext } from "react";
import UsersListContext from "../context/UserListContext"

function useUsersList() {
    return useContext(UsersListContext);
}

export default useUsersList;