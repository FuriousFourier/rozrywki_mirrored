import React, {Component} from 'react'
import {BrowserRouter as Router, Route, Link} from "react-router-dom";
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
                                <Link to={'/'}>Reporting</Link>
                            </Navbar.Brand>
                        </Navbar.Header>
                        <Nav>
                            <NavItem eventKey={1}>
                                <Link to={'/players/'}>Online Channel Reports</Link>
                            </NavItem>
                            <NavItem eventKey={2}>
                                <Link to={'/bets/'}>Bet Status UI</Link>
                            </NavItem>
                        </Nav>
                    <Nav pullRight>
                        <NavItem eventKey={3} href={'/logout/'}>
                            logałt kurła
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
