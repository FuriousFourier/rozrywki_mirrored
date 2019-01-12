import React, {Component} from 'react'
import './App.css'
import {PageHeader, Table} from "react-bootstrap"
import DateTimeRangePicker from '@wojtekmaj/react-datetimerange-picker';


class OnlineChannelReportsUI extends Component {

    interval = null;
    state = {
        players: [],
        period: null
    };


    componentDidMount() {
        this.getPlayers();
        this.interval = setInterval(this.getPlayers, 3000);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    getPlayers = () => {
        const isPeriodDefined = this.state.period
        const definedStart = isPeriodDefined && this.state.period[0]
        const definedEnd = isPeriodDefined && this.state.period[1]
        const requestUri = isPeriodDefined ?
            `/api/players/${definedStart.toISOString()}/${definedEnd.toISOString()}` :
            '/api/players'
        fetch(requestUri)
            .then(response => response.text())
            .then(message => {
                this.setState({
                    players: JSON.parse(message)
                })
            })
            .catch(reason => {
                console.warn(reason)
            })
    };

    handlePeriodChange = (period) => {
        this.setState({
            period
        })
    };

    render() {
        return (
            <div>
                <PageHeader>
                    Online Channel Reports UI
                </PageHeader>
                <DateTimeRangePicker
                    onChange={this.handlePeriodChange}
                    value={this.state.period}
                />
                <br/>
                <br/>
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
