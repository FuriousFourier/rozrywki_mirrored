import React, {Component} from 'react'
import './App.css'
import {Nav, Navbar, NavItem} from "react-bootstrap"
import BetStatusUI from './BetStatusUI'
import OnlineChannelReportsUI from './OnlineChannelReportsUI'


class App extends Component {

    state = {
        showBetStatusUI: false,
        showChannelReportsUI: false
    }

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
                                showBetStatusUI: true,
                                showChannelReportsUI: false
                            })
                        }}>
                            Bet Status
                        </NavItem>
                        <NavItem eventKey={1} onClick={() => {
                            this.setState({
                                showBetStatusUI: false,
                                showChannelReportsUI: true
                            })
                        }}>
                            Online Channel Reports
                        </NavItem>
                    </Nav>
                </Navbar>
                {this.state.showBetStatusUI && <BetStatusUI/>}
                {this.state.showChannelReportsUI && <OnlineChannelReportsUI/>}
            </div>
        )
    }
}

export default App
