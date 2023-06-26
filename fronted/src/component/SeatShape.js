import React from 'react';
import {useEffect, useState} from 'react';
import {Link, useLocation} from 'react-router-dom';
import "../css/buy.css";


function SeatShape() {
const [message, setMessage] = useState([]);
const location = useLocation();
const pro = location.state.program;

function sp(a,b){
    const re = [];
    let index;
    for(index=0; index<a.length; index += b){
        let t;
        t = a.slice(index, index+b);
        re.push(t);
    }
    return re;
}
function max(a){
    let re=0;
    let index;
    for(index=0;index<a.length;index++){
        if(a[index].s_col > re)
            re = a[index].s_col;
    }
    return ++re;
}


function SeatShape() {
const [message, setMessage] = useState([]);
const location = useLocation();
const pro = location.state.program;
const [searchParams, setSearchParams] = useSearchParams();
const n = searchParams.get('num');

    useEffect(()=>{
        setInterval(()=> {
        fetch(`/List/${pro}`)
        .then((response) => {
            return response.json();
        })
        .then(function (data) {
            setMessage(sp(data,max(data)));
            console.log(data);
        });
        },1000);

    }, )

   return(
        <div className="r">
                <h1>{pro} {n}</h1>
                {message.map(function (m, index){
                       return (
                           <div key={index} className="bu2">
                            <ul>
                               {m.map(function (t, index2){
                               return(
                                   <li key={index2}>
                                       <Link to={{
                                           pathname:`/Pur/${t.id}`
                                       }}
                                             state={{num: t}}>
                                           <button key={t.id}
                                                   title={t.program_num}
                                                   className={"btn"+(`${t.checking}`==="true" ? 'T': 'F')}
                                                   disabled={t.checking}
                                           ></button>
                                       </Link>
                                   </li>
                               )})}
                       </ul>
                   </div>
               )
           })
           }
       </div>
   );
}
export default SeatShape;