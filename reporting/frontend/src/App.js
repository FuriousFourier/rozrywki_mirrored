import React, {Component} from 'react'
import {BrowserRouter as Router, Route} from "react-router-dom";
import './App.css'
import {Nav, Navbar, NavItem} from "react-bootstrap"
import BetStatusUI from './BetStatusUI'
import OnlineChannelReportsUI from './OnlineChannelReportsUI'

function Home() {
    return (
        <div>
            <h2>Home</h2>
        </div>
    );
}

class App extends Component {

    render() {
        return (
            <Router>
                <div className="App">

                    <Navbar>
                        <Navbar.Header>
                            <Navbar.Brand>
                                <a href="/">Reporting</a>
                            </Navbar.Brand>
                        </Navbar.Header>
                        <Nav>
                            <NavItem eventKey={1} href='/players/'>
                                Online Channel Reports
                            </NavItem>
                            <NavItem eventKey={1} href='/bets/'>
                                Bet Status UI
                            </NavItem>
                        </Nav>
                    </Navbar>
                    <Route path="/" exact component={Home}/>
                    <Route path="/bets/" component={BetStatusUI}/>
                    <Route path="/players/" component={OnlineChannelReportsUI}/>
                </div>
            </Router>
        )
    }
}

export default App
