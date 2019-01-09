import React, {Component} from 'react'
import {BrowserRouter as Router, Link, Route} from "react-router-dom";
import './App.css'
import {Nav, Navbar, NavItem} from "react-bootstrap"
import BetStatusUI from './BetStatusUI'
import OnlineChannelReportsUI from './OnlineChannelReportsUI'
import {LinkContainer} from 'react-router-bootstrap';

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
                            <LinkContainer to={'/players/'}>
                                <NavItem eventKey={1}>Online Channel Reports</NavItem>
                            </LinkContainer>
                            <LinkContainer to={'/bets/'}>
                                <NavItem eventKey={2}>Bet Status UI</NavItem>
                            </LinkContainer>
                        </Nav>
                        <Nav pullRight>
                            <NavItem eventKey={3} href={'/logout/'}>
                                Logout
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
