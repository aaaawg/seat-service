import React from 'react';
import "../css/buy.css";
import axios from "axios";
import {useQuery} from "react-query";
import Grid from 'react-grid-layout';

function max(a){
    let re=0;
    let index;
    for(index=0;index<a.length;index++){
        if(a[index].s_col > re)
            re = a[index].s_col;
    }
    return ++re;
}

function SeatNewList(program){
    let n=0;
    const {data, isSuccess} = useQuery(
    ["seat_list"],
    () => axios.get("http://localhost:3000/List/1"),{
        onSuccess: data => {
        }
    });
    n = max(isSuccess);
    console.log(data, isSuccess)
    return(
    <Grid cols={n} width={10}>
    {n}
        {isSuccess ? (
             data.data.map((seat, index) => (
                    <div key={index}>
                        id: {seat.id} col: {seat.s_col} ch: {seat.checking}
                    </div>
          ))
        ) : null}
    </Grid>
    );
}
export default SeatNewList;