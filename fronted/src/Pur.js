import './App.css';
import {Link, useLocation} from 'react-router-dom';

function Pur() {
    const location = useLocation();
    const num = location.state.num;

    function test() {
        fetch('/check', {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                num: num.num,
                check: num.check,
                c : num.c
            }),
        }).then(res=>{
            if(res.ok){
            alert("okok");
            }
        })
    }

  return (
     <div className="App">
    <h1> {num.num} {num.c} </h1>
    <Link to="/Buy">
        <button onClick={test}>구매</button>
    </Link>
     </div>
  );
}

export default Pur;