import React, {Component} from 'react';
import { Link } from 'react-router-dom';

class CustomerTableRow extends Component{

    render() {
        return (
            <tr>
                <td>
                    {this.props.customer.id}
                </td>
                <td>
                    {this.props.customer.firstName}
                </td>
                <td>
                    {this.props.customer.lastName}
                </td>
                <td>
                    {this.props.customer.email}
                </td>
                <td>
                    {this.props.customer.phone}
                </td>
                <td>
                    <Link to={"/details_customer/"+this.props.customer.id} className="btn btn-primary">Accounts</Link>
                </td>
            </tr>
        );
    }
}

export default CustomerTableRow;