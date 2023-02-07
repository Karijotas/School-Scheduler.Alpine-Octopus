import React from 'react'
import ViewGroups from './EditPages/ViewGroups'

// Here we should pass the selection as props, and then map the values from Backend to the tables
// I've just done here a simple if else that is only for example, not how it should work.
// Change/Edit this section to suit your case. 
// Best is to create a new file for your implementation based on this one, and later we will refactor and merge the best options
// One could just pass the newly created file here, after return

function ObjectList(props) {
    if (props === 'groups') {
        return (
            <ViewGroups/>
        )
    }
    if (props === 'subjects') {
        return (
            <p>
                Dalykai:
            </p>

        )
    }
    if (props === 'teachers') {
        return (
            <p>
                Mokytojai:
            </p>

        )
    }
    if (props === 'shifts') {
        return (
            <p>
                Pamainos:
            </p>

        )
    }
    if (props === 'rooms') {
        return (
            <p>
                Klasės:
            </p>

        )
    } 
    if (props === 'program') {
        return (
            <p>
                Programos:
            </p>

        )
    }
    if (props === 'modules'){
        return (
            <p>
                Moduliai:
            </p>
           
        )
    }
}

export default ObjectList
