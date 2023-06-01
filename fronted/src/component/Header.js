import React, {useState} from 'react';
import { BiHome, BiMoon } from "react-icons/bi";
import { AiOutlineMenu } from "react-icons/ai";
import { RiUserSmileLine, RiBookOpenLine } from "react-icons/ri";
import { TbMessageCircle } from "react-icons/tb";
import "../font/Font.css";
import "../css/Header.css";

function Header(){
    const [isOpen, setMenu] = useState(false);
      const toggleMenu = () => {
            setMenu(isOpen => !isOpen);
      }

        return(
        <div className="header-st">
            <header id="menu" className="mainMenu">
                <div className="m-trigger" key="header-st">
                    <button title="m-icon11" className="m-icon" onClick={()=>toggleMenu()}>
                        <AiOutlineMenu size="25" />
                        <span></span>
                    </button>
                    <nav className={isOpen ? "show-menu" : "hide-menu"}>
                        <div className="m-scroll">
                            <ul className="m-sc-menu">
                                <li>
                                    <a href="#!" id="a1">
                                        <BiHome size="1.2vw" color="#777e79" />
                                        <strong>메인페이지</strong>
                                    </a>
                                    <ul className="m-sub-menu">
                                        <li className="line">
                                        <a className="m-icon-a" href="#!" id="b1">
                                            메인 서브1
                                        </a>
                                        </li>
                                        <li className="line">
                                            <a className="m-icon-a" href="#!" id="b2">메인 서브2</a>
                                        </li>
                                    </ul>
                                </li>

                                <li className="line">
                                    <a href="/List" id="a2">
                                        <RiBookOpenLine size="1.2vw" color="#777e79" />
                                        <strong>강연</strong>
                                    </a>
                                    <ul className="m-sub-menu"> </ul>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>

                <ul className="head1">
                    <li className="logo-c">
                        <a href="#!">
                        <img src={process.env.PUBLIC_URL + "Pic/lo.png"} alt="로고" />
                        <h1 className="lo">ST</h1>
                        </a>
                    </li>
                </ul>
                <ul className="head2">

                    <li className="version">
                        <a href="#!">
                            <BiMoon size="30"/>
                        </a>
                    </li>
                    <li className="message">
                        <a href="#!">
                            <TbMessageCircle size="30" />
                        </a>
                    </li>
                    <li className="user">
                        <a href="#!">
                            <RiUserSmileLine size="35"/>
                        </a>
                    </li>
                </ul>
            </header>
        </div>
        );
}
export default Header;