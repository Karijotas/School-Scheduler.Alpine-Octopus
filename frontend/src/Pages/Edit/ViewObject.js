import React from 'react'
<<<<<<< HEAD
import ViewGroups from './EditPages/ViewGroups'
import ViewPrograms from './EditPages/ViewPrograms'

=======
import { EditObject } from './EditPages/EditObject'
import { ViewGroups } from './EditPages/ViewGroups'
>>>>>>> 1a0601285eba9a06826238d3bdaf7c2a8ae96e70
// Here we should pass the selection as props, and then map the values from Backend to the tables
// I've just done here a simple if else that is only for example, not how it should work.
// Change/Edit this section to suit your case. 
// Best is to create a new file for your implementation based on this one, and later we will refactor and merge the best options
// One could just pass the newly created file here, after return

export function ObjectList(props) {
    if (props === 'groups') {
        if (props === 'groups/{$id}') {
            return (
                <EditObject />
            )
        } else {
              return (
            <ViewGroups />
        )
        }
      
    }



    if (props === 'subjects') {
        return (
            <p>
                <EditObject />
            </p>

        )
    }
    if (props === 'teachers') {
        return (
            <p>

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
            <ViewPrograms />

        )
    }
    if (props === 'modules') {
        return (
            <p>
                Moduliai:
            </p>

        )
    }
}

