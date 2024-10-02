import { Routes } from '@angular/router';

import { IndexComponent } from './car/index/index.component'
import { ViewComponent } from './car/view/view.component'
import { CreateComponent } from './car/create/create.component'
import { EditComponent } from './car/edit/edit.component'
import { IndexBookingComponent } from './booking/index-booking/index-booking.component';

export const routes: Routes = [
    { path: '', component: IndexComponent },
    { path: 'car/index', component: IndexComponent },
    { path: 'car/:carId/view', component: ViewComponent },
    { path: 'car/create', component: CreateComponent },
    { path: 'car/:carId/edit', component: EditComponent},
    { path: 'booking', component: IndexBookingComponent}
];
