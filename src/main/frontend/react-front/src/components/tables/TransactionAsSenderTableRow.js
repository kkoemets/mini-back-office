import React, {Component} from 'react';

class TransactionAsSenderTableRow extends Component{

    render() {
        return (
            <tr>
                <td>
                    {this.props.trans.id}
                </td>
                <td>
                    {this.props.trans.description}
                </td>
                <td>
                    {this.props.trans.amount}
                </td>
                <td>
                    {this.props.trans.senderAccount.name}
                </td>
                <td>
                    {this.props.trans.receiverAccount.name}
                </td>
            </tr>
        );
    }
}

export default TransactionAsSenderTableRow;