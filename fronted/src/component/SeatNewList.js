import React from 'react';
import axios from "axios";
import {useQuery} from "react-query";
import GridLayout from 'react-grid-layout';

function max(a){
    let re=0;
    let te = a ? Object.keys(a).length : 0;;
    let index;
    for(index=0;index<te;index++){
        if(a[index].s_col > re)
            re = a[index].s_col;
    }
    ++re;
    return re;
}


//자료 정렬 ㄱㄱ
//길이 받아오기 ok
const fetchData = () => {
    return axios.get("http://localhost:3000/List/1");
}

function SeatNewList(){
    let n;
    const {data, isSuccess} = useQuery(
    'seat_list',
    fetchData,
    {
        select: (data) => data.data,
    }
    );
    console.log(data);

    n = data ? Object.keys(data).length : 0;
    n =max(data);

    return(
    <>
    {n}
       {isSuccess && data && Array.isArray(data) ? (
             data.map((seats,index) => (
                    <div key={index}>
                        {seats.id}
                    </div>
          ))
        ) : null}
    </>
    );
}
export default SeatNewList;