import React, {Component} from 'react';

export default class TableFilter extends Component {

    static filterMethod = (filter, row, column) => {
        const id = filter.pivotId || filter.id;
        return (row[id] !== undefined
            ? String(row[id].toString().toLowerCase()).includes(filter.value.toLowerCase())
            : true)
    };
}