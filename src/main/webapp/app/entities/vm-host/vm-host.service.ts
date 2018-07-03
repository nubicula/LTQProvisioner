import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVMHost } from 'app/shared/model/vm-host.model';

type EntityResponseType = HttpResponse<IVMHost>;
type EntityArrayResponseType = HttpResponse<IVMHost[]>;

@Injectable({ providedIn: 'root' })
export class VMHostService {
    private resourceUrl = SERVER_API_URL + 'api/vm-hosts';

    constructor(private http: HttpClient) {}

    create(vMHost: IVMHost): Observable<EntityResponseType> {
        return this.http.post<IVMHost>(this.resourceUrl, vMHost, { observe: 'response' });
    }

    update(vMHost: IVMHost): Observable<EntityResponseType> {
        return this.http.put<IVMHost>(this.resourceUrl, vMHost, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVMHost>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVMHost[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
