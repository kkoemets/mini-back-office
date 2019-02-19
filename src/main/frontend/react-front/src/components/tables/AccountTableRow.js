import React, {Component} from 'react';
import {Link} from "react-router-dom";

class AccountTableRow extends Component{

    render() {
        return (
            <tr>
                <td>
                    {this.props.account.id}
                </td>
                <td>
                    {this.props.account.name}
                </td>
                <td>
                    {this.props.account.balance}
                </td>
                <td>
                    <Link to={"/details_account/"+this.props.account.id}
                          className="btn btn-primary">
                        Transactions
                    </Link>
                </td>
            </tr>
        );
    }
}

export default AccountTableRow;