import React, {Component} from 'react'
import './App.css'
import {PageHeader, Table} from "react-bootstrap"


class OnlineChannelReportsUI extends Component {

    state = {
        players: []
    }


    componentDidMount() {
        this.getPlayers()
        setInterval(this.getPlayers, 3000)
    }

    getPlayers = () => {
        fetch('/api/players')
            .then(response => response.text())
            .then(message => {
                this.setState({
                    players: JSON.parse(message)
                })
            })
    }

    render() {
        console.log(this.state)
        return (
            <div>
                <PageHeader>
                    Online Channel Reports UI
                </PageHeader>
                <Table striped bordered condensed hover>
                    <thead>
                    <tr>
                        <th>Online player</th>
                        <th>Overall bet slips</th>
                        <th>Stakes overall</th>
                        <th>Refunds overall</th>
                        <th>Overall revenue</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.players.map((player, idx) => (
                        <tr key={idx}>
                            <td>{player.name}</td>
                            <td>{player.betSlipCount}</td>
                            <td>{player.stakesOverall.pretty}</td>
                            <td>{player.refundsOverall.pretty}</td>
                            <td>{player.overallRevenue.pretty}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </div>
        )
    }
}

export default OnlineChannelReportsUI
