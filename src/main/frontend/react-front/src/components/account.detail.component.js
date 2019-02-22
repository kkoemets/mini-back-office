import React, { Component } from 'react';
import ReactTable from 'react-table';

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

    filterMethod = (filter, row, column) => {
        const id = filter.pivotId || filter.id;
        return row[id] !== undefined ? String(row[id].toLowerCase()).startsWith(filter.value.toLowerCase()) : true
    }

    render() {
        const columns = [
            {
                Header: 'ID',
                accessor: 'id',
                id: 'id',
                style: {
                    textAlign: 'center'
                },
            },
            {
                Header: 'Description',
                accessor: 'description',
                style:{ 'whiteSpace': 'unset'
                },
                filterable: false,
            },
            {
                Header: 'Amount',
                accessor: 'amount',
                style: {
                    textAlign: 'center'
                },
                filterable: false,
            },
            {
                Header: 'Transaction Date',
                accessor: 'transactionDate',
                style:{ 'whiteSpace': 'unset'
                }
            },
            {
                Header: 'Sender Account',
                accessor: 'senderAccount.name',
                style: {
                    textAlign: 'center'
                },
            },
            {
                Header: 'Receiver Account',
                accessor: 'receiverAccount.name',
                style: {
                    textAlign: 'center'
                },
            },
        ];

        return (
            <div>
                <h3 align="center">Transaction list for account: {this.state.account}</h3>
                <ReactTable
                    columns = {columns}
                    data = {this.state.transactions}
                    filterable
                    defaultFilterMethod={this.filterMethod}
                    defaultPageSize={10}
                    noDataText={'Loading transactions, please wait...'}
                    showPaginationTop
                    showPaginationBottom={false}
                    className="-striped -highlight"
                />
            </div>
        )
    }
}