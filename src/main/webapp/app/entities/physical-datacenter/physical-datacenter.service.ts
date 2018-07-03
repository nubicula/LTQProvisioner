import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPhysicalDatacenter } from 'app/shared/model/physical-datacenter.model';

type EntityResponseType = HttpResponse<IPhysicalDatacenter>;
type EntityArrayResponseType = HttpResponse<IPhysicalDatacenter[]>;

@Injectable({ providedIn: 'root' })
export class PhysicalDatacenterService {
    private resourceUrl = SERVER_API_URL + 'api/physical-datacenters';

    constructor(private http: HttpClient) {}

    create(physicalDatacenter: IPhysicalDatacenter): Observable<EntityResponseType> {
        return this.http.post<IPhysicalDatacenter>(this.resourceUrl, physicalDatacenter, { observe: 'response' });
    }

    update(physicalDatacenter: IPhysicalDatacenter): Observable<EntityResponseType> {
        return this.http.put<IPhysicalDatacenter>(this.resourceUrl, physicalDatacenter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPhysicalDatacenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPhysicalDatacenter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
