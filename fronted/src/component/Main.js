import React, {Component} from 'react';
import Header from "./Header";
import "../css/Main.css";
import Footer from "./Footer";

class Main extends Component{
    render(){
        return(
        <>
            <Header/>
            <div className="con">
                <div className="cal">
                    <h2>Cal</h2>
                </div>

                <div className="lecture">
                    <h2>Lecture</h2>
                </div>

                <div className="culture">
                    <h2>Culture</h2>
                </div>

                <div className="group">
                     <h2>Group</h2>
                 </div>
            </div>
            <Footer/>
        </>
        );
    }
}

export default Main;