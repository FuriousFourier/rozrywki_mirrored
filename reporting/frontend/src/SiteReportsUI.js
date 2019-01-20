import React, {Component} from 'react'
import './App.css'
import {PageHeader, Table} from "react-bootstrap"
import DateTimeRangePicker from '@wojtekmaj/react-datetimerange-picker';


class OnlineChannelReportsUI extends Component {

    interval = null;
    state = {
        sites: [],
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
            `/api/sites/${definedStart.toISOString()}/${definedEnd.toISOString()}` :
            '/api/sites'
        fetch(requestUri)
            .then(response => response.text())
            .then(message => {
                this.setState({
                    sites: JSON.parse(message)
                })
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
                    Site Reports UI
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
                        <th>Site</th>
                        <th>Open bet slips</th>
                        <th>Open bet slips stakes</th>
                        <th>Won bet slips</th>
                        <th>Won bet slips stakes</th>
                        <th>Lost bet slips</th>
                        <th>Lost bet slips stakes</th>
                        <th>Paid in</th>
                        <th>Paid out</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.sites.map((site, idx) => (
                        <tr key={idx}>
                            <td>{site.name}</td>
                            <td>{site.openBetSlips.betSlipsCount}</td>
                            <td>{site.openBetSlips.overallStakes.pretty}</td>
                            <td>{site.wonBetSlips.betSlipsCount}</td>
                            <td>{site.wonBetSlips.overallStakes.pretty}</td>
                            <td>{site.lostBetSlips.betSlipsCount}</td>
                            <td>{site.lostBetSlips.overallStakes.pretty}</td>
                            <td>{site.paidIn.pretty}</td>
                            <td>{site.paidOut.pretty}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </div>
        )
    }
}

export default OnlineChannelReportsUI
