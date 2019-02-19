import React, { Component } from 'react';
import TransactionAsSenderTableRow from "./tables/TransactionAsSenderTableRow";
import TransactionAsReceiverTableRow from "./tables/TransactionAsReceiverTableRow";

export default class AccountDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            transactionsAsSender: [],
            transactionsAsReceiver: []
        };
    }

    async componentDidMount() {
        console.log(this.props.match.params.id);
        const response = await fetch('/api/transaction/account/sender/' + this.props.match.params.id);
        const body = await response.json();
        this.setState({transactionsAsSender: body, isLoading: false});

        console.log(this.props.match.params.id);
        const response1 = await fetch('/api/transaction/account/receiver/' + this.props.match.params.id);
        const body1 = await response1.json();
        this.setState({transactionsAsReceiver: body1, isLoading: false});
    }

    tableRowSender() {
        return this.state.transactionsAsSender.map(function(object, i) {
            return <TransactionAsSenderTableRow trans={object} key={i}/>;
        });
    }

    tableRowReceiver() {
        return this.state.transactionsAsReceiver.map(function(object, i) {
            return <TransactionAsReceiverTableRow trans={object} key={i}/>;
        });
    }

    render() {
        return (
            <div>
                <h3 align="center">Transactions As Sender</h3>
                <table className="table table-striped" style={{marginTop: 20}}>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Description</th>
                        <th>Amount</th>
                        <th>Sender</th>
                        <th>Receiver</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tableRowSender()}
                    </tbody>
                </table>
                <h3 align="center">Transactions As Receiver</h3>
                <table className="table table-striped" style={{marginTop: 20}}>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Description</th>
                        <th>Amount</th>
                        <th>Sender</th>
                        <th>Receiver</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tableRowReceiver()}
                    </tbody>
                </table>
            </div>
        )
    }
}