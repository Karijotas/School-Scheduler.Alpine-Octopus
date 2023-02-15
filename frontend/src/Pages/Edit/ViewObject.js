import React from 'react'
import { EditObject } from './EditPages/EditObject'
import { ModuleList } from './EditPages/ModuleList'
import { SubjectList } from './EditPages/SubjectList'
import { ViewGroups } from './EditPages/ViewGroups'
import { EditSubject } from './EditSubject'
import {ViewRooms} from './EditPages/ViewRooms'

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
        if (props === 'subjects/{$id}') {
            return (
                <EditSubject />
            )
        } else {
              return (
                <SubjectList />
        )
        }
      
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
            <ViewRooms />

        )
    }
    if (props === 'program') {
        return (
            <p>
                Programos:
            </p>

        )
    }
    if (props === 'modules') {
        if (props === 'modules/{$id}') {
            return (
                <EditSubject />
            )
        } else {
              return (
                <ModuleList />
        )
        }
      
    }
}

