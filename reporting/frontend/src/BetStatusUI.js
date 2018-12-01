import React, {Component} from 'react';
import './App.css';
import {PageHeader, Table} from "react-bootstrap";


class BetStatusUI extends Component {

    state = {
        bets: []
    };


    componentDidMount() {
        this.getBets()
    }

    getBets = () => {
        fetch('/api/bets')
            .then(response => response.text())
            .then(message => {
                this.setState({
                    bets: JSON.parse(message)
                });
            });
    };

    render() {
        console.log(this.state)
        return (
            <div>
                <PageHeader>
                    Bet Status UI
                </PageHeader>
                <Table striped bordered condensed hover>
                    <thead>
                    <tr>
                        <th>Bet ID</th>
                        <th>Gracz</th>
                        <th>Wydarzenie</th>
                        <th>Status zakładu</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.bets.map((bet, idx) => (
                        <tr key={idx}>
                            <td>{bet.betId}</td>
                            <td>{bet.player}</td>
                            <td>{bet.event}</td>
                            <td>{bet.status}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default BetStatusUI
