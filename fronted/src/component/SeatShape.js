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
    let re;
    let index;
    for(index=0;index<a.length;index++)
        if(a[index].col > re)
            re = a[index].col;
    re++;
    return re;
}
    useEffect(()=>{
        setInterval(()=> {
        console.log("running");

        fetch("/Test/"+{pro})
        .then((response) => {
            return response.json();
        })
        .then(function (data) {
            setMessage(sp(data,max(data)));
        });
        },1000);

    }, )

   return(
        <div className="r">
                <h1>{pro}</h1>
                {message.map(function (m, index){
                       return (
                           <div key={index} className="bu2">
                            <ul>
                               {m.map(function (t, index2){
                               return(
                               <li key={index2}>
                               <Link to={{
                                               pathname:`/Pur/${t.num}`
                                         }}
                               state={{num: t}}>
                                               <button key={t.num}
                                               title={t.num}
                                               className={"btn"+(`${t.check}`==="true" ? 'T': 'F')}
                                               disabled={t.check}
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