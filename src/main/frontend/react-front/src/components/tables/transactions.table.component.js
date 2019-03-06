import React, {Component} from 'react';

import ReactTable from 'react-table';
import 'react-table/react-table.css';

import TableFilter from './table.filter.component';

export default class TransactionsTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            transactions: []
        }
    }



    static alignColumnsMiddle(columns) {
        for (let column of columns) {
            if(TransactionsTable.isIgnoredHeaderWhenAligningMiddle(column.Header)) {
                continue;
            }
            column.style = {
                textAlign: 'center'
            }
        }
    }

    static isIgnoredHeaderWhenAligningMiddle(columnHeader) {
        return columnHeader === 'Description' || columnHeader === 'Transaction Date'
    }


    render() {
        this.state.transactions = this.props.transactions;

        const columns = [
            {
                Header: 'ID',
                accessor: 'id',
                id: 'id',
            },
            {
                Header: 'Description',
                accessor: 'description',
                style:{ 'whiteSpace': 'unset'
                },
                filterable: false,
            },
            {
                Header: 'Amount, EUR',
                accessor: 'amount',
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
            },
            {
                Header: 'Receiver Account',
                accessor: 'receiverAccount.name',
            },
        ];

        TransactionsTable.alignColumnsMiddle(columns);

        return (
            <div>
                <ReactTable
                    columns = {columns}
                    data = {this.state.transactions}
                    filterable
                    defaultFilterMethod={TableFilter.filterMethod}
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