import React from "react";
import "../css/buy.css";
import SeatNewList from "../component/SeatNewList";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";

const queryClient = new QueryClient();

function NewList(){
    return(
    <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools initialIsOpen={true} />
        <SeatNewList />
    </QueryClientProvider>
    );
}

export default NewList;