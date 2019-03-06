import React, {Component} from 'react';

import ReactTable from 'react-table';
import 'react-table/react-table.css';

import TableFilter from './table.filter.component';

export default class AccountsTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
          accounts: []
        };
    }


    showTransactions(id) {
        window.location.assign("/details_account/" + id);
    }

    static alignColumnsMiddle(columns) {
        for (let column of columns) {
            column.style = {
                textAlign: 'center'
            }
        }
    }

    render() {
        this.state.accounts = this.props.accounts;

        const columns = [
            {
                Header: 'ID',
                accessor: 'id',
                width : 70,
                minWidth : 70
            },
            {
                Header: 'Account Name',
                accessor: 'name',
            },
            {
                Header: 'Account Balance, EUR',
                accessor: 'balance',
                filterable: false,
            },
            {
                Cell: props => {
                    return (
                        <button className="btn btn-primary"
                                onClick={ () => {
                                    this.showTransactions(props.row.id);
                                }}
                        >Transactions</button>
                    )
                },
                sortable: false,
                filterable: false
            }
        ];

        AccountsTable.alignColumnsMiddle(columns);

        return (
            <div>
                <ReactTable
                    columns={columns}
                    data={this.state.accounts}
                    filterable
                    defaultFilterMethod={TableFilter.filterMethod}
                    defaultPageSize={5}
                    noDataText={'Loading accounts, please wait...'}
                    showPaginationTop
                    showPaginationBottom={false}
                    className="-striped -highlight"
                />
            </div>
        )
    }
}