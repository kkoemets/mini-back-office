import React, { Component } from 'react';

import TransactionsTable from "./tables/transactions.table.component";

export default class AccountDetail extends Component {

    constructor(props) {
        super(props);
        this.state = {
            transactions: [],
            account: ''
        };
    }

    async componentDidMount() {
        const response = await fetch('/api/transaction/account/sender/' + this.props.match.params.id);
        const asSender = await response.json();
        this.state.account = asSender[0].senderAccount.name;
        const response1 = await fetch('/api/transaction/account/receiver/' + this.props.match.params.id);
        const asReceiver = await response1.json();
        const sum = asSender.concat(asReceiver);
        this.setState({transactions: sum, isLoading: false});
    }

    render() {
        return (
            <div>
                <h3 align="center">Transactions for account: {this.state.account}</h3>
                <TransactionsTable transactions={this.state.transactions}/>
            </div>
        )
    }
}