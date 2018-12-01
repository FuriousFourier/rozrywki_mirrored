import React, {Component} from 'react';
import './App.css';
import {Nav, Navbar, NavItem} from "react-bootstrap";
import BetStatusUI from './BetStatusUI'


class App extends Component {

    state = {
        showBetStatusUI: false
    };

    render() {
        return (
            <div className="App">
                <Navbar>
                    <Navbar.Header>
                        <Navbar.Brand>
                            <a href="#">Reporting</a>
                        </Navbar.Brand>
                    </Navbar.Header>
                    <Nav>
                        <NavItem eventKey={1} onClick={() => {
                            this.setState({
                                showBetStatusUI: true
                            })
                        }}>
                            Bet Status
                        </NavItem>
                    </Nav>
                </Navbar>
                {this.state.showBetStatusUI && <BetStatusUI/>}
            </div>
        );
    }
}

export default App;
