import React, { Component } from 'react';
import ReactTable from "react-table";

export default class CustomerDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            accounts: [],
            customer: ''
        };
    }

    async componentDidMount() {
        const response = await fetch('/api/account/customer/' + this.props.match.params.id);
        const body = await response.json();
        const customer = body[0].customer;
        this.state.customer = customer.firstName + ' ' + customer.lastName;
        this.setState({accounts: body, isLoading: false});
    }

    showTransactions(id) {
        window.location.assign("/details_account/" + id);
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
                style: {
                    textAlign: 'center'
                },
                width : 70,
                minWidth : 70
            },
            {
                Header: 'Account Name',
                accessor: 'name',
                style: {
                    textAlign: 'center'
                }
            },
            {
                Header: 'Account Balance',
                accessor: 'balance',
                filterable: false,
                style: {
                    textAlign: 'center'
                }
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
                style: {
                    textAlign: 'center'
                },
                sortable: false,
                filterable: false
            }
        ]
        return (
            <div>
                <h3 align="center">Account list for customer: {this.state.customer}</h3>
                <ReactTable
                    columns={columns}
                    data={this.state.accounts}
                    filterable
                    defaultFilterMethod={this.filterMethod}
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