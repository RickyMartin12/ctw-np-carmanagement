import { Routes } from '@angular/router';

import { IndexComponent } from './car/index/index.component'
import { ViewComponent } from './car/view/view.component'
import { CreateComponent } from './car/create/create.component'
import { EditComponent } from './car/edit/edit.component'
import { IndexBookingComponent } from './booking/index-booking/index-booking.component';
import { CreateBookingComponent } from './booking/create-booking/create-booking.component';
import { ViewBookingComponent } from './booking/view-booking/view-booking.component';
import { EditBookingComponent } from './booking/edit-booking/edit-booking.component';

export const routes: Routes = [
    { path: '', component: IndexComponent },
    { path: 'car/index', component: IndexComponent },
    { path: 'car/:carId/view', component: ViewComponent },
    { path: 'car/create', component: CreateComponent },
    { path: 'car/:carId/edit', component: EditComponent},
    { path: 'booking', component: IndexBookingComponent},
    { path: 'reservation/create', component: CreateBookingComponent },
    { path: 'reservation/:rersId/view', component: ViewBookingComponent },
    { path: 'reservation/:rersId/edit', component: EditBookingComponent },
];
