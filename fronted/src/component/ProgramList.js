import React from 'react';
import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import "../css/ProgramList.css";

function ProgramList() {
const [message, setMessage] = useState([]);

    useEffect(()=>{
        fetch("/admin/program/List")
        .then((response) => {
            return response.json();
        })
        .then(function (data) {
            setMessage(data);
        });
        },[]);

    return(
    <div className="list">
        {message.map(function(m,index){
            return(
                <div key={index} className="program-list">
                <Link to= {`/Buy?num=${m.programNum}`}
                state={{program: m.programNum}}>
                <button>
                    <div className="list-outside">
                        <h1> {m.title} </h1>
                        <div className="list-inside">
                            <p> 장소 : {m.place} </p>
                            <p> 실시 날짜 : {m.endDate} </p>
                            <p> 신청 기간 : {m.startDate} ~ {m.endDate} </p>
                        </div>
                    </div>
                </button>
                </Link>
                </div>
            )
        })}
    </div>
    );
}

export default ProgramList;